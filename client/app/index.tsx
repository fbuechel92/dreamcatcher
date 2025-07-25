import React from 'react';
import * as eva from '@eva-design/eva';
import { ApplicationProvider } from '@ui-kitten/components';
import MainScreen from "../screens/MainScreen";

export default () => (
  <ApplicationProvider {...eva} theme={eva.light}>
    <MainScreen />
  </ApplicationProvider>
);