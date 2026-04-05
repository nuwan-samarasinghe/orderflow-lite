import { ChangeDetectionStrategy, Component, signal } from "@angular/core";
import { RouterLink, RouterLinkActive } from "@angular/router";

import { MATERIAL_IMPORTS } from "../../shared/material/material-imports";

@Component({
  selector: "app-shell",
  standalone: true,
  imports: [RouterLink, RouterLinkActive, ...MATERIAL_IMPORTS],
  templateUrl: "./app-shell.component.html",
  styleUrls: ["./app-shell.component.scss"],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AppShellComponent {
  protected readonly opened = signal(true);

  protected toggleSidenav(): void {
    this.opened.update((value) => !value);
  }
}
