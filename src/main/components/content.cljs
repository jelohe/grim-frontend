(ns components.content)

(defn content [note]
  (let [note-content (:content note)]
    [:section.note-content note-content]))
