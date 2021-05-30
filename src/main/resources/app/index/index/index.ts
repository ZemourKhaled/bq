import './index.css';

const template = require("./index.html");

import controller from "./index.controller";

const component={
    template,
    controller,
    controllerAs: 'index'
}

const IndexComponent = {
    name: 'index',
    component
};

export default IndexComponent;