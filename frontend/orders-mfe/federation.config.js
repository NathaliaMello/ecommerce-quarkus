const { withNativeFederation, shareAll } = require('@angular-architects/native-federation/config');

module.exports = withNativeFederation({
  name: 'orders-mfe',

  exposes: {
    './Component': './orders-mfe/src/app/app.ts',
    './Routes': './orders-mfe/src/app/app.routes.ts',
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
  ],

  features: {
    ignoreUnusedDeps: true
  }
});