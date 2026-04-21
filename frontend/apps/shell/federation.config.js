const { withNativeFederation, shareAll } = require('@angular-architects/native-federation/config');

module.exports = withNativeFederation({
  name: 'shell',

  remotes: {
    'users-mfe': 'http://localhost:4201/remoteEntry.json',
    'orders-mfe': 'http://localhost:4202/remoteEntry.json',
    'notifications-mfe': 'http://localhost:4203/remoteEntry.json',
  },


  shared: {
    ...shareAll({ singleton: true, strictVersion: true, requiredVersion: 'auto' }),
    '@ecommerce/shared-auth': { 
      singleton: true, 
      strictVersion: false,
      eager: true,
      requiredVersion: 'auto'
    },
  },

  skip: [
    'rxjs/ajax',
    'rxjs/fetch',
    'rxjs/testing',
    'rxjs/webSocket',
     'keycloak-js', 
    // Add further packages you don't need at runtime
  ],

  // Please read our FAQ about sharing libs:
  // https://shorturl.at/jmzH0

  features: {
    // New feature for more performance and avoiding
    // issues with node libs. Comment this out to
    // get the traditional behavior:
    ignoreUnusedDeps: true
  }
});
