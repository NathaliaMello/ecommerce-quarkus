const { withNativeFederation, shareAll } = require('@angular-architects/native-federation/config');

module.exports = withNativeFederation({
  name: 'users-mfe',
  exposes: {
    './Component': './users-mfe/src/app/app.ts',
    './Routes': './users-mfe/src/app/app.routes.ts',
  },

  shared: {
    ...shareAll({ singleton: true, strictVersion: true, requiredVersion: 'auto' }),
    '@angular/router': {
      singleton: true,
      strictVersion: false,
      requiredVersion: 'auto'
    },
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
    '@angular/router/types/_router_module-chunk',
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
