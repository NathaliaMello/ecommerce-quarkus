import { initFederation } from '@angular-architects/native-federation';

initFederation({
  'users-mfe': 'http://localhost:4201/remoteEntry.json',
  'orders-mfe': 'http://localhost:4202/remoteEntry.json',
  'notifications-mfe': 'http://localhost:4203/remoteEntry.json',
})
  .catch(err => console.error(err))
  .then(_ => import('./bootstrap'))
  .catch(err => console.error(err));
