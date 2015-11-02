angular.module('ngBoilerplate.orderform', [
    'services',
    'ui.router',
    'placeholders',
    'ui.bootstrap'
])

    .config(function config($stateProvider) {
        $stateProvider.state('orderform', {
            url: '/orderform',
            abstract: true,
            views: {
                "main": {
                    controller: 'OrderFormCtrl',
                    templateUrl: 'orderform/orderform.tpl.html'
                }
            },
            data: {pageTitle: 'Order'}
        });
    })
    .config(function config($stateProvider) {
        $stateProvider.state('orderform.step1', {
            url: '/',
            views: {
                "main": {
                    controller: 'OrderFormStep1Ctrl',
                    templateUrl: 'orderform/orderform.step1.tpl.html'
                }
            },
            data: {pageTitle: 'Order'}
        });
    })
    .config(function config($stateProvider) {
        $stateProvider.state('orderform.step2', {
            url: '/step2',
            views: {
                "main": {
                    controller: 'OrderFormStep2Ctrl',
                    templateUrl: 'orderform/orderform.step2.tpl.html'
                }
            },
            data: {pageTitle: 'Order Persoonlijke gegevens'}
        });
    })
    .config(function config($stateProvider) {
        $stateProvider.state('orderform.confirm', {
            url: '/confirm',
            views: {
                "main": {
                    controller: 'OrderFormConfirmCtrl',
                    templateUrl: 'orderform/orderform.confirm.tpl.html'
                }
            },
            data: {pageTitle: 'Confirm order'}
        });
    })

    .controller('OrderFormCtrl', ['$scope', function ($scope) {

        $scope.order = {"p1": true};
    }])
    .controller('OrderFormStep1Ctrl', ['$scope', '$state', function OrderFormStep1Ctrl($scope, $state) {

        $scope.next = function () {
            var atLeaseOneProductSelected = $scope.order.p1 || $scope.order.p2 || $scope.order.p3;

            if (atLeaseOneProductSelected) {
                $state.go('orderform.step2');
            }
        };

        $scope.isValidOrder = function () {
            return $scope.order.p1 || $scope.order.p2 || $scope.order.p3;
        };

    }])
    .controller('OrderFormStep2Ctrl', ['$scope', '$state', function OrderFormStep2Ctrl($scope, $state) {

        $scope.back = function () {
            $state.go('orderform.step1');
        };

        $scope.next = function () {
            $state.go('orderform.confirm');
        };

        $scope.isValidOrder = function () {
            return ($scope.order.aanhef != null && $scope.order.aanhef.length !== 0) &&
                ($scope.order.voorletters != null && $scope.order.voorletters.length !== 0) &&
                ($scope.order.achternaam != null && $scope.order.achternaam.length !== 0);
        };
    }])
    .controller('OrderFormConfirmCtrl', ['$scope', '$state', 'API', function OrderFormConfirmCtrl($scope, $state, API) {

        $scope.back = function () {
            $state.go('orderform.step2');
        };

        $scope.confirm = function () {
            $scope.order = API.persons.post($scope.order);
        };

    }])

;
