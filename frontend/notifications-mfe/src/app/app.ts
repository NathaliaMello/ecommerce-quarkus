import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  imports: [RouterModule],
  selector: 'app-notifications-mfe',
  template: `
    <div>
      <h2>Notificações</h2>
      <p>MFE de notificações carregado com sucesso!</p>
      <router-outlet></router-outlet>
    </div>
  `,
})
export class App {
  protected title = 'notifications-mfe';
}