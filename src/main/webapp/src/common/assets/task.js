/**
 * Dit is de service laag die presentatie los koppelt van de interface naar de backend.
 * Hiervoor is ngStorage (bower install ngResource --save-dev)
 * en ngResource (bower install angular-resource --save-dev) nodig.
 *
 * Toevoegen aan build.config.js: 'vendor/angular-resource/angular-resource.js'
 *
 */
var services = angular.module('services',
    ['ngResource']);

services.factory('API', ['$resource',
        function ($resource) {
            return {
                persons: $resource('/data/persons/:id', {}, {
                    get: {method: 'GET', params: {id: ''}},
                    query: {method: 'GET', isArray: true},
                    post: {method: 'POST'},
                    update: {method: 'PUT', params: {id: '@id'}},
                    remove: {method: 'DELETE', params: {id: '@id'}}
                }),
                boeken: $resource('/data/boeken/:id', {}, {
                    get: {method: 'GET', params: {id: ''}},
                    query: {method: 'GET', isArray: true},
                    post: {method: 'POST'},
                    update: {method: 'PUT', params: {id: '@id'}},
                    remove: {method: 'DELETE', params: {id: '@id'}}
                })
            };

//            return {
//                // Opties voor opzoeken landen en vestigingen
//                // Docs: https://docs.angularjs.org/api/ngResource/service/$resource
//
//                // Alle personen
//                // GET /data/persons
//                // Laat de domeinnaam hier weg om CORS problemen te voorkomen
//                persons: $resource('/data/persons', {},
//                    {query: {method: 'GET', isArray: true}},
//                    {store: {method: 'PUT'}}),
//
//                // Persoon
//                // GET /data/persons/:id
//                person: $resource('/data/persons/:id', {id: '@id'},
//                    {query: {method: 'GET', isArray: false}}),
//
////                // Locaties met zoekterm
////                // Given a template /path/:verb and parameter {verb:'greet', salutation:'Hello'}
////                // results in URL /path/greet?salutation=Hello
////                // GET /api/locaties/[locatieCode]
////                locationsForTerm: $resource('/api/locaties', {search: '@search'},
////                    {query: {method: 'GET', isArray: true}}),
//
//                // Alle locaties in land
//                // GET /api/locaties/[landCode]
//                locationsForCountry: $resource('/api/locaties/:land', {land: '@land'},
//                    {query: {method: 'GET', isArray: true}}),
//
////                // Alle locaties met land en zoekterm
////                // GET /api/locaties/DE?search=iets
//                locationsForCountryForTerm: $resource('/api/locaties/:land', {land: '@land', search: '@search'},
//                    {query: {method: 'GET', isArray: true}}),
//
//                // Een locatie op basis van locatiecode
//                location: $resource('/api/locaties/:land/:locatie', {land: '@land', locatie: '@locatie'},
//                    {query: {method: 'GET', isArray: false}}),
//
//
//                vehicleCategories: $resource('/api/voertuigcategorieen', {},
//                    {query: {method: 'GET', isArray: true}}),
//
//                vehicles: $resource('/api/voertuigen', {},
//                    {query: {method: 'GET', isArray: true}}),
//                vihiclesForLocationsForCategorie: $resource('/api/voertuigen', {categorie: '@categorie'},
//                    {query: {method: 'GET', isArray: true}}),
//                vehicleDetails: $resource('/api/voertuigen/:vehicleCode', {vehicleCode: '@vehicleCode'},
//                    {query: {method: 'GET', isArray: false}}),
//
//                inloggen: $resource('/api/login', {}, {login: {method: 'POST', isArray: false}}),
//                reserveren: function(){
//                    return $resource('/api/reserveringen', {},
//                        {save: {method: 'POST', isArray: false, headers: getHeader()}});
//                }
//            };
            //function getHeader(){
            //    return {'AUTH-TOKEN': $localStorage.token};
            //}
        }
    ]
);