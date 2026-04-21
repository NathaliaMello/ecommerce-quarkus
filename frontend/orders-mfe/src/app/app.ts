import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  imports: [RouterModule],
  selector: 'app-orders-mfe',
  template: `
    <div>
      <h2>Pedidos</h2>
      <p>MFE de pedidos carregado com sucesso!</p>
      <router-outlet></router-outlet>
    </div>
  `,
})
export class App {
  protected title = 'orders-mfe';
}
