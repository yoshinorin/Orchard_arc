import * as React from 'react';
import * as ReactDOM from "react-dom";
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import {AppBar, Toolbar, ToolbarGroup, ToolbarTitle, MenuItem, IconMenu, RaisedButton, IconButton, FontIcon, SvgIcon } from 'material-ui';

import HomeIcon from "./icons/Home";

class NavBar extends React.Component {
  constructor() {
    super();
  }

  render() {
    return (
      <MuiThemeProvider>
        <Toolbar>
          <ToolbarGroup>
            <HomeIcon/>
          </ToolbarGroup>
        </Toolbar>
      </MuiThemeProvider>
    );
  }
}

ReactDOM.render(
  <NavBar />,
  document.getElementById("navbar")
);