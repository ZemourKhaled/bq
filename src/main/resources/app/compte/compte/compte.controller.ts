import {UserService, default as userServiceName} from "../../service/UserService";

export default class indexCtrl {

    private static readonly $inject = ["$state", userServiceName];

    constructor(private $state, private userService:UserService) {
    }

}
