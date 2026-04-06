import {
  ChangeDetectionStrategy,
  Component,
  computed,
  signal,
} from "@angular/core";

import { MATERIAL_IMPORTS } from "../../../shared/material/material-imports";

@Component({
  selector: "app-dashboard",
  standalone: true,
  imports: [...MATERIAL_IMPORTS],
  templateUrl: "./dashboard.page.component.html",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DashboardComponent {
  private readonly refreshCount = signal(0);

  protected readonly metrics = computed(() => {
    const offset = this.refreshCount();
    return [
      { label: "Orders Today", value: 18 + offset },
      { label: "Revenue", value: `$${(8450 + offset * 100).toLocaleString()}` },
      { label: "Low Stock Alerts", value: 3 },
    ];
  });

  protected refresh(): void {
    this.refreshCount.update((value) => value + 1);
  }
}
