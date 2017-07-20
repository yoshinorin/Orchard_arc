import * as React from 'react';
import * as ReactDOM from "react-dom";
import * as Colors from 'material-ui/styles/colors';
import SvgIcon from 'material-ui/SvgIcon';

const iconStyles = {
  marginRight: 24,
};

const SvgBackIcon = (props: any) => (
  <SvgIcon {...props}>
    <path d="M15.41 16.09l-4.58-4.59 4.58-4.59L14 5.5l-6 6 6 6z"/>
  </SvgIcon>
);

const BackIcon = () => (
  <div>
    <SvgBackIcon style={iconStyles} />
  </div>
);

export default BackIcon;