import './accueil.css';

const template = require("./accueil.html");

import controller from "./accueil.controller";

const component={
    template,
    controller,
    controllerAs: 'accueil'
}

const AccueilComponent = {
    name: 'accueil',
    component
};

export default AccueilComponent;