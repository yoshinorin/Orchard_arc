import * as React from 'react';
import * as ReactDOM from "react-dom";
import * as Colors from 'material-ui/styles/colors';
import SvgIcon from 'material-ui/SvgIcon';

const iconStyles = {
  marginRight: 24,
};

const SvgHomeIcon = (props: any) => (
  <SvgIcon {...props}>
    <path d="M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z" />
  </SvgIcon>
);

const HomeIcon = () => (
  <div>
    <SvgHomeIcon style={iconStyles} />
  </div>
);

export default HomeIcon;