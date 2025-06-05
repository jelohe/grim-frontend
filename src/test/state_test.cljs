(ns state-test
  (:require [cljs.test :refer [deftest is testing run-tests async use-fixtures]]
            [state]
            [reagent.core :as r]))

(use-fixtures :each
  {:before (fn [] (reset! state/opened-notes []))})

(deftest select-note!-test
  (testing "Marks the selected note"
    (let [initial-notes [{:name "Nota 1" :selected false}
                         {:name "Nota 2" :selected false}]]
      (reset! state/opened-notes initial-notes)
      (state/select-note! "Nota 1")
      (is (true? (-> @state/opened-notes first :selected)) "First note should be selected")
      (is (false? (-> @state/opened-notes second :selected) "Rest of the notes should NOT be selected"))))

  (testing "Selecting a non-existent note does nothing"
    (let [initial-notes [{:name "Nota 1" :selected false}
                         {:name "Nota 2" :selected false}]]
      (reset! state/opened-notes initial-notes)
      (state/select-note! "Missing note")
      (is (every? false? (map :selected @state/opened-notes)) "No notes should be selected")))

  (testing "Selecting a note when another is already selected"
    (let [initial-notes [{:name "Nota 1" :selected true}
                         {:name "Nota 2" :selected false}]]
      (reset! state/opened-notes initial-notes)
      (state/select-note! "Nota 2")
      (is (false? (-> @state/opened-notes first :selected)) "Previously selected not should be unselected")
      (is (true? (-> @state/opened-notes second :selected) "New note should be selected"))))
  )

(deftest selected-note-test
  (testing "Returns the selected note"
    (let [notes-with-selected [{:name "Nota A" :selected false}
                               {:name "Nota B" :selected true}]]
      (reset! state/opened-notes notes-with-selected)
      (is (= "Nota B" (-> (state/selected-note @state/opened-notes) :name)))))

  (testing "Returns the first selected note if there are multiple"
    (let [notes-with-selected [{:name "Nota A" :selected true}
                               {:name "Nota B" :selected true}]]
      (reset! state/opened-notes notes-with-selected)
      (is (= "Nota A" (-> (state/selected-note @state/opened-notes) :name)))))

  (testing "Returns nil if there aren't selected notes"
    (let [notes-with-selected [{:name "Nota A" :selected false}
                               {:name "Nota B" :selected false}]]
      (reset! state/opened-notes notes-with-selected)
      (is (= nil (-> (state/selected-note @state/opened-notes) :name)))))
  )
