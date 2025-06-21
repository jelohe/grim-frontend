(ns views.grimorio
  (:require [components.header :refer [header]]
            [components.sidebar :refer [sidebar]]
            [components.content :refer [content]]
            [state]
            ))

(defn grimorio []
  (let [notes @state/opened-notes]
    [:<>
     (header)
     [:main
      (sidebar notes)
      (content (state/selected-note notes))]]))

