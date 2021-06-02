import './compte.css';

const template = require("./compte.html");

import controller from "./compte.controller";

const component={
    template,
    controller,
    controllerAs: 'compte'
}

const CompteComponent = {
    name: 'compte',
    component
};

export default CompteComponent;