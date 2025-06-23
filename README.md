# Grimorio frontend

Frontend to create a note-taking website, based on the [Zettlecasten method](https://en.wikipedia.org/wiki/Zettelkasten).

Requires a backend to authenticate and to store, serve and update the notes. **TODO**.

Developed in Clojure using [CLJS](https://clojurescript.org/) with [Reagent](https://github.com/reagent-project/reagent).


## Development

### **Prepare**

Ensure you have Node and npm, then do a `npm install`.


### **Run**

##### Stub backend server

Cd into the directory `cd backend-stub`

`clj -M:up` to open a stub server on `localhost:3001`

`clj -M:test` to run stub tests

##### Frontend dev server

`npm start` to open a server on `localhost:3000`

`npm run test` frontend unit testing
