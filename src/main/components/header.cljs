(ns components.header)

(defn header []
  [:header
   [:h1 "Grimorio"]
   [:div.searchbar-wrapper
    [:input {:class "searchbar" :placeholder "search..."}]]])
