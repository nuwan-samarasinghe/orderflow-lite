import { ChangeDetectionStrategy, Component } from "@angular/core";
import { RouterLink } from "@angular/router";

import { MATERIAL_IMPORTS } from "../../shared/material/material-imports";

@Component({
  selector: "app-not-found",
  standalone: true,
  imports: [RouterLink, ...MATERIAL_IMPORTS],
  templateUrl: "./not-found.page.component.html",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NotFoundComponent {}
