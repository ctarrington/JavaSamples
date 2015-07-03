var carstoreApp = angular.module('carstoreApp', ['ngRoute', 'ngResource', 'carControllers']);

carstoreApp.factory('CarStorage', ['$resource',
    function($resource) {

        return $resource("/cars", {}, {
            get: {method: 'GET', cache: false, isArray: true},
            save: {method: 'POST', cache: false, isArray: false},
            update: {method: 'PUT', cache: false, isArray: false},
            delete: {method: 'DELETE', cache: false, isArray: false}
        });

    }]);

carstoreApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/cars', {
                templateUrl: 'partials/car-list.html',
                controller: 'CarListCtrl'
            }).
            when('/cars/:carId', {
                templateUrl: 'partials/car-detail.html',
                controller: 'CarDetailCtrl'
            }).
            otherwise({
                redirectTo: '/cars'
            });
    }]);

var carControllers = angular.module('carControllers', []);

carControllers.controller('MainCtrl', ['$scope', '$http', '$location',
    function ($scope, $http, $location) {

    }]);

carControllers.controller('CarListCtrl', ['$scope', '$resource', 'CarStorage',
    function ($scope, $resource, CarStorage) {

        function loadCars() {
            CarStorage.get({},
                function success(response) {
                    $scope.cars = response;
                },
                function error(errorResponse) {
                    console.log("Error:" + JSON.stringify(errorResponse));
                }
            );
        }

        $scope.saveCar = function() {
            CarStorage.save($scope.newCar, function success(response) {
                    console.log( "save success response"+JSON.stringify(response) );
                    $scope.newCar = null;
                    loadCars();
                },
                function error(errorResponse) {
                    alert("Error:" + JSON.stringify(errorResponse));
                }
            );
        };

        $scope.deleteCar = function(car) {
            CarStorage.delete({id:car.id}, function success(response) {
                    console.log( "delete success response"+JSON.stringify(response) );
                    loadCars();
                },
                function error(errorResponse) {
                    alert("Error:" + JSON.stringify(errorResponse));
                }
            );

        }

        loadCars();
        $scope.orderProp = 'make';
        $scope.newCar = null;

    }]);

carControllers.controller('CarDetailCtrl', ['$scope', '$routeParams',
    function($scope, $routeParams) {
        $scope.carId = $routeParams.carId;
    }]);