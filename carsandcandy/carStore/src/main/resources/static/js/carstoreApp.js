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
            when('/candy', {
                templateUrl: 'partials/candy-detail.html',
                controller: 'CarListCtrl'
            }).
            when('/login', {
                templateUrl: 'partials/login.html',
                controller: 'LoginCtrl'
            }).
            otherwise({
                redirectTo: '/login'
            });
    }]);

var carControllers = angular.module('carControllers', []);

carControllers.controller('MainCtrl', ['$scope', '$http', '$location',
    function ($scope, $http, $location) {

        var changeRoute = function(evt) {
            $location.search('candyRoute', evt.detail.newRoute);
            $scope.$apply();
        };

        document.getElementById("messageBus").addEventListener("csRouteChangeRequest", changeRoute, false);

        $scope.candyStoreInitialized = false;
        $http.get('/cars/user').
            success(function(data, status, headers, config) {
                $scope.user = data.name;
            }).
            error(function(data, status, headers, config) {
                $scope.user = null;
            });
        $scope.assertionForCandy = '';
        $scope.redirectUrl = function() { return 'http://cars.dev:8070/#/cars'; };

        $scope.myAssertionForCandy = null;
        $scope.assertionForCandy = function(value) {
            if (value) {
                $scope.myAssertionForCandy = value;
            }
            else {
                return $scope.myAssertionForCandy;
            }
        };

        $scope.isCandyTime = function() {
            return ($location.url().indexOf('candy') >= 0);
        };

        $scope.$on('$locationChangeSuccess', function(evt, newUrl, oldUrl) {
            if (newUrl.indexOf('candy') >= 0) {
                if (!$scope.candyStoreInitialized) {
                    candyStore.initialize();
                    $scope.candyStoreInitialized = true;
                } else {
                    var event = new CustomEvent("csLocationChanged", {
                            detail: { oldUrl: oldUrl, newUrl: newUrl },
                            bubbles: true,
                            cancelable: true
                        }
                    );

                    document.getElementById("messageBus").dispatchEvent(event);
                }

            } else if ($scope.candyStoreInitialized && newUrl.indexOf('candy') === -1) {
                candyStore.destroy();
                $scope.candyStoreInitialized = false;
            }


        });

        $scope.isLoggedIn = function() {
            return ( $scope.user != null );
        };

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

carControllers.controller('LoginCtrl', ['$scope', '$http', '$timeout',
    function($scope, $http, $timeout) {
        $scope.status = '';

        $scope.login = {
            username: null,
            password: null
        };

        $scope.login = function() {
            $http.post('/cars/login', {name: $scope.login.username, password: $scope.login.password}).
                success(function(data, status, headers, config) {
                    $scope.assertionForCandy(data.description);

                    $timeout(function() {
                        var formElement = $('#sendAssertionForm');
                        formElement.submit();
                    }, 200);
                }).
                error(function(data, status, headers, config) {
                    $scope.status = 'ERROR';
                });

            $scope.status = 'Pending';
        };

    }]);