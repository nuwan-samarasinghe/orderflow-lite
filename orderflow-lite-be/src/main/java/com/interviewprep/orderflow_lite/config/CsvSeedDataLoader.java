package com.interviewprep.orderflow_lite.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.interviewprep.orderflow_lite.config.seed_data.SeedDataProperties;
import com.interviewprep.orderflow_lite.entity.Address;
import com.interviewprep.orderflow_lite.entity.Customer;
import com.interviewprep.orderflow_lite.entity.CustomerOrder;
import com.interviewprep.orderflow_lite.entity.Inventory;
import com.interviewprep.orderflow_lite.entity.NotificationLog;
import com.interviewprep.orderflow_lite.entity.OrderItem;
import com.interviewprep.orderflow_lite.entity.OutboxEvent;
import com.interviewprep.orderflow_lite.entity.Payment;
import com.interviewprep.orderflow_lite.entity.Product;
import com.interviewprep.orderflow_lite.enums.NotificationStatus;
import com.interviewprep.orderflow_lite.enums.NotificationType;
import com.interviewprep.orderflow_lite.enums.OrderStatus;
import com.interviewprep.orderflow_lite.enums.OutboxStatus;
import com.interviewprep.orderflow_lite.enums.PaymentStatus;
import com.interviewprep.orderflow_lite.repository.AddressRepository;
import com.interviewprep.orderflow_lite.repository.CustomerOrderRepository;
import com.interviewprep.orderflow_lite.repository.CustomerRepository;
import com.interviewprep.orderflow_lite.repository.InventoryRepository;
import com.interviewprep.orderflow_lite.repository.NotificationLogRepository;
import com.interviewprep.orderflow_lite.repository.OrderItemRepository;
import com.interviewprep.orderflow_lite.repository.OutboxEventRepository;
import com.interviewprep.orderflow_lite.repository.PaymentRepository;
import com.interviewprep.orderflow_lite.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvSeedDataLoader {

    private static final Pattern FILE_PATTERN =
            Pattern.compile("^(customers|addresses|products|inventory|orders|order_items|payments|notification_logs|outbox_events)_(\\d{4}-\\d{2}-\\d{2})\\.csv$");

    private final SeedDataProperties properties;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final CustomerOrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final NotificationLogRepository notificationLogRepository;
    private final OutboxEventRepository outboxEventRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void loadSeedData() {
        if (!properties.isEnabled()) {
            log.info("Seed data loading disabled");
            return;
        }

        if (customerRepository.count() > 0) {
            log.info("Database already has data. Skipping seed loading");
            return;
        }

        try {
            Map<String, Resource> files = resolveLatestFiles();
            loadCustomers(files.get("customers"));
            loadAddresses(files.get("addresses"));
            loadProducts(files.get("products"));
            loadInventory(files.get("inventory"));
            loadOrders(files.get("orders"));
            loadOrderItems(files.get("order_items"));
            loadPayments(files.get("payments"));
            loadNotificationLogs(files.get("notification_logs"));
            loadOutboxEvents(files.get("outbox_events"));

            log.info("CSV seed data loading completed successfully");
        } catch (IOException ex) {
            log.error("Failed to load CSV seed data", ex);
            throw new IllegalStateException("Seed data loading failed", ex);
        }
    }

    private Map<String, Resource> resolveLatestFiles() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Resource> allResources = new ArrayList<>();

        Resource[] classpathResources =
                resolver.getResources("classpath*:" + properties.getClasspathLocation() + "/*.csv");
        Resource[] externalResources =
                resolver.getResources("file:" + properties.getExternalLocation() + "/*.csv");

        allResources.addAll(Arrays.asList(classpathResources));
        allResources.addAll(Arrays.asList(externalResources));

        Map<String, ResourceHolder> latestByEntity = new HashMap<>();

        for (Resource resource : allResources) {
            String filename = resource.getFilename();
            if (filename == null) {
                continue;
            }

            Matcher matcher = FILE_PATTERN.matcher(filename);
            if (!matcher.matches()) {
                log.warn("Skipping file with invalid naming convention: {}", filename);
                continue;
            }

            String entity = matcher.group(1);
            String date = matcher.group(2);

            ResourceHolder current = new ResourceHolder(entity, date, resource);
            ResourceHolder existing = latestByEntity.get(entity);

            if (existing == null || current.date().compareTo(existing.date()) > 0) {
                latestByEntity.put(entity, current);
            }
        }

        Map<String, Resource> result = new HashMap<>();
        latestByEntity.forEach((k, v) -> result.put(k, v.resource()));

        log.info("Resolved latest seed files: {}", result.keySet());
        return result;
    }

    private void loadCustomers(Resource resource) throws IOException {
        if (resource == null) return;

        for (CSVRecord row : parse(resource)) {
            String email = row.get("email");
            if (customerRepository.findByEmail(email).isPresent()) {
                continue;
            }

            Customer customer = Customer.builder()
                    .fullName(row.get("fullName"))
                    .email(email)
                    .phone(row.get("phone"))
                    .build();

            customerRepository.save(customer);
        }
        log.info("Customers loaded");
    }

    private void loadAddresses(Resource resource) throws IOException {
        if (resource == null) return;

        for (CSVRecord row : parse(resource)) {
            Customer customer = customerRepository.findByEmail(row.get("customerEmail"))
                    .orElseThrow(() -> new IllegalStateException("Customer not found for address: " + row.get("customerEmail")));

            Address address = Address.builder()
                    .line1(row.get("line1"))
                    .line2(emptyToNull(row.get("line2")))
                    .city(row.get("city"))
                    .state(row.get("state"))
                    .country(row.get("country"))
                    .zipCode(row.get("zipCode"))
                    .customer(customer)
                    .build();

            addressRepository.save(address);
        }
        log.info("Addresses loaded");
    }

    private void loadProducts(Resource resource) throws IOException {
        if (resource == null) return;

        for (CSVRecord row : parse(resource)) {
            String sku = row.get("sku");
            if (productRepository.findBySku(sku).isPresent()) {
                continue;
            }

            Product product = Product.builder()
                    .sku(sku)
                    .name(row.get("name"))
                    .description(row.get("description"))
                    .price(new BigDecimal(row.get("price")))
                    .category(row.get("category"))
                    .active(Boolean.parseBoolean(row.get("active")))
                    .build();

            productRepository.save(product);
        }
        log.info("Products loaded");
    }

    private void loadInventory(Resource resource) throws IOException {
        if (resource == null) return;

        for (CSVRecord row : parse(resource)) {
            Product product = productRepository.findBySku(row.get("productSku"))
                    .orElseThrow(() -> new IllegalStateException("Product not found for inventory: " + row.get("productSku")));

            if (inventoryRepository.findByProductId(product.getId()).isPresent()) {
                continue;
            }

            Inventory inventory = Inventory.builder()
                    .product(product)
                    .availableQuantity(Integer.parseInt(row.get("availableQuantity")))
                    .reservedQuantity(Integer.parseInt(row.get("reservedQuantity")))
                    .build();

            inventoryRepository.save(inventory);
        }
        log.info("Inventory loaded");
    }

    private void loadOrders(Resource resource) throws IOException {
        if (resource == null) return;

        for (CSVRecord row : parse(resource)) {
            String orderNumber = row.get("orderNumber");
            if (orderRepository.findByOrderNumber(orderNumber).isPresent()) {
                continue;
            }

            Customer customer = customerRepository.findByEmail(row.get("customerEmail"))
                    .orElseThrow(() -> new IllegalStateException("Customer not found for order: " + row.get("customerEmail")));

            CustomerOrder order = CustomerOrder.builder()
                    .orderNumber(orderNumber)
                    .customer(customer)
                    .status(OrderStatus.valueOf(row.get("status")))
                    .totalAmount(new BigDecimal(row.get("totalAmount")))
                    .build();

            orderRepository.save(order);
        }
        log.info("Orders loaded");
    }

    private void loadOrderItems(Resource resource) throws IOException {
        if (resource == null) return;

        for (CSVRecord row : parse(resource)) {
            CustomerOrder order = orderRepository.findByOrderNumber(row.get("orderNumber"))
                    .orElseThrow(() -> new IllegalStateException("Order not found for item: " + row.get("orderNumber")));

            Product product = productRepository.findBySku(row.get("productSku"))
                    .orElseThrow(() -> new IllegalStateException("Product not found for order item: " + row.get("productSku")));

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(Integer.parseInt(row.get("quantity")))
                    .unitPrice(new BigDecimal(row.get("unitPrice")))
                    .lineTotal(new BigDecimal(row.get("lineTotal")))
                    .build();

            orderItemRepository.save(item);
        }
        log.info("Order items loaded");
    }

    private void loadPayments(Resource resource) throws IOException {
        if (resource == null) return;

        for (CSVRecord row : parse(resource)) {
            CustomerOrder order = orderRepository.findByOrderNumber(row.get("orderNumber"))
                    .orElseThrow(() -> new IllegalStateException("Order not found for payment: " + row.get("orderNumber")));

            if (paymentRepository.findByOrderId(order.getId()).isPresent()) {
                continue;
            }

            Payment payment = Payment.builder()
                    .order(order)
                    .paymentReference(row.get("paymentReference"))
                    .status(PaymentStatus.valueOf(row.get("status")))
                    .amount(new BigDecimal(row.get("amount")))
                    .paidAt(LocalDateTime.parse(row.get("paidAt")))
                    .build();

            paymentRepository.save(payment);
        }
        log.info("Payments loaded");
    }

    private void loadNotificationLogs(Resource resource) throws IOException {
        if (resource == null) return;

        for (CSVRecord row : parse(resource)) {
            CustomerOrder order = orderRepository.findByOrderNumber(row.get("orderNumber"))
                    .orElseThrow(() -> new IllegalStateException("Order not found for notification log: " + row.get("orderNumber")));

            NotificationLog notification = NotificationLog.builder()
                    .order(order)
                    .type(NotificationType.valueOf(row.get("type")))
                    .recipient(row.get("recipient"))
                    .status(NotificationStatus.valueOf(row.get("status")))
                    .sentAt(LocalDateTime.parse(row.get("sentAt")))
                    .build();

            notificationLogRepository.save(notification);
        }
        log.info("Notification logs loaded");
    }

    private void loadOutboxEvents(Resource resource) throws IOException {
        if (resource == null) return;

        for (CSVRecord row : parse(resource)) {
            OutboxEvent event = OutboxEvent.builder()
                    .aggregateType(row.get("aggregateType"))
                    .aggregateId(Long.parseLong(row.get("aggregateId")))
                    .eventType(row.get("eventType"))
                    .payload(row.get("payload"))
                    .status(OutboxStatus.valueOf(row.get("status")))
                    .build();

            outboxEventRepository.save(event);
        }
        log.info("Outbox events loaded");
    }

    private List<CSVRecord> parse(Resource resource) throws IOException {
        try (
                InputStream inputStream = resource.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .setTrim(true)
                        .build()
                        .parse(reader)
        ) {
            return parser.getRecords();
        }
    }

    private String emptyToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }

    private record ResourceHolder(String entity, String date, Resource resource) {
    }
}