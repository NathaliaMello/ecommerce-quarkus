import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  imports: [RouterModule],
  selector: 'app-users-mfe',
  template: `
    <div>
      <h2>Usuários</h2>
      <p>MFE de usuários carregado com sucesso!</p>
      <router-outlet></router-outlet>
    </div>
  `,
})
export class App {
  protected title = 'users-mfe';
}
