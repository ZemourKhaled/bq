import {bootstrap, module} from "angular";
import Index from "./index/index";
import uirouter from "@uirouter/angularjs";
import IndexComponent from "./index/index";
import {default as userServiceName, UserService} from "../service/UserService";

module('index', [
    uirouter,
])
    .component(Index.name, Index.component)
    .service(userServiceName, UserService)
    .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {


        const index = {
            name: "index",
            state: {
                url: "/index",
                views: {
                    'main@': {
                        component: IndexComponent.name
                    }
                }
            }
        };






        $urlRouterProvider.otherwise("/index");

        $stateProvider
            .state(index.name, index.state)

    }])
;
bootstrap(document.body, ['index']);
