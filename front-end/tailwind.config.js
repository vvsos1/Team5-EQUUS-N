/** @type {import('tailwindcss').Config} */

export const content = ['./src/**/*.{js,jsx,ts,tsx}'];
export const theme = {
  extend: {
    colors: {
      gray: {
        0: '#FCFCFC',
        100: '#F2F2F2',
        200: '#E1E1E1',
        300: '#C6C6C6',
        400: '#707070',
        500: '#565656',
        600: '#404040',
        700: '#313131',
        800: '#262626',
        900: '#191919',
      },
      lime: {
        0: '#CBEF85',
        100: '#CBEF85',
        200: '#CBEF85',
        300: '#CBEF85',
        400: '#CBEF85',
        500: '#C2EC6F',
        600: '#BAEA5D',
        700: '#A4E327',
        800: '#9BD91D',
        900: '#8CC617',
      },
    },
  },
};
export const plugins = [];
