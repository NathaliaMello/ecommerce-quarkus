import { initFederation } from '@angular-architects/native-federation';

initFederation({
  'notificationsMfe': 'http://localhost:4200/remoteEntry.json',
  'ordersMfe': 'http://localhost:4200/remoteEntry.json',
  'usersMfe': 'http://localhost:4200/remoteEntry.json'
})
  .catch(err => console.error(err))
  .then(_ => import('./bootstrap'))
  .catch(err => console.error(err));
