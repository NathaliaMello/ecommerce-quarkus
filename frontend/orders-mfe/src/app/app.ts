import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-orders-mfe',
  imports: [RouterOutlet],
  template: `<router-outlet />`,
})
export class App {}