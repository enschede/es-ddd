angular.module( 'ngBoilerplate.bootstraptest', [
  'ui.router',
  'placeholders',
  'ui.bootstrap'
])

.config(function config( $stateProvider ) {
  $stateProvider.state( 'bootstraptest', {
    url: '/bootstraptest',
    views: {
      "main": {
        controller: 'BootstrapTestCtrl',
        templateUrl: 'bootstraptest/bootstraptest.tpl.html'
      }
    },
    data:{ pageTitle: 'What is It?' }
  });
})
.config(function config( $stateProvider ) {
  $stateProvider.state( 'bootstraptest_grid', {
    url: '/bootstraptest/grid',
    views: {
      "main": {
        controller: 'BootstrapTestCtrl',
        templateUrl: 'bootstraptest/gridtest.tpl.html'
      }
    },
    data:{ pageTitle: 'What is It?' }
  });
})
.config(function config( $stateProvider ) {
  $stateProvider.state( 'bootstraptest_photo', {
    url: '/bootstraptest/photo',
    views: {
      "main": {
        controller: 'BootstrapTestCtrl',
        templateUrl: 'bootstraptest/phototest.tpl.html'
      }
    },
    data:{ pageTitle: 'Bootstraps Photo' }
  });
})
.config(function config( $stateProvider ) {
  $stateProvider.state( 'bootstraptest_button', {
    url: '/bootstraptest/button',
    views: {
      "main": {
        controller: 'BootstrapTestCtrl',
        templateUrl: 'bootstraptest/buttontest.tpl.html'
      }
    },
    data:{ pageTitle: 'Bootstraps Button' }
  });
})

.controller( 'BootstrapTestCtrl', function BootstrapTestCtrl( $scope ) {
  // This is simple a demo for UI Boostrap.
  $scope.dropdownDemoItems = [
    "The first choice!",
    "And another choice for you.",
    "but wait! A third!"
  ];
})

;
