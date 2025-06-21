# Grimorio frontend

Frontend to create a note-taking website, based on the [Zettlecasten method](https://en.wikipedia.org/wiki/Zettelkasten).

Requires a backend to authenticate and to store, serve and update the notes. **TODO**.

Developed in Clojure using [CLJS](https://clojurescript.org/) with [Reagent](https://github.com/reagent-project/reagent).


### Development

* **Prepare**
  Ensure you have Node and npm, then do a `npm install`.

* **Run**

##### Stub backend server

`cd backend-stub`
`clj -M -m backend-stub.core` to open a stub on `localhost:3001`

##### Frontend dev server

`npm start` to open a server on `localhost:3000`
