# Basketball Simulator Web App

The web app for Basketball Simulator.

## Prerequisites

To get started with development, you will need a browser that runs JavaScript and some extra prerequisites:

- [Node.js](https://nodejs.org) - _Package manager_
- [npm](https://www.npmjs.com/get-npm) - _This should come with your Node installation._
- A (relatively recent) browser that allows JavaScript - more information on supported browsers can be found in
  the [`package.json`](package.json#L62) file.

## Build Setup

To install dependencies:

```bash 
$ npm install
```

To serve the application in development mode, in a development server:

```bash 
$ npm run-script dev-start
```

To build for development:

```bash 
$ npm run dev-build
```

To build for production:

```bash 
$ npm run build
```

Launch application in production mode with necessary optimisations:

```bash 
$ npm run start
```

## Testing

Testing, with a coverage report, can be performed using the following command:

```bash 
$ npm test
```

The coverage report can be viewed in your default browser using:

```bash 
$ npm run-script coverage
```

The [`test/unit/components`](test/unit/components) directory contains tests for the `.vue` single file components.

## Deployment

Deployment to the hosting platform can be performed using:

```bash 
$ npm run-script deploy
```

## Authors

* **Ernest Nkansah-Badu** - [GitHub](https://github.com/Ernxst)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## Acknowledgements

