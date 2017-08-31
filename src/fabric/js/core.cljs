(ns fabric.js.core
    (:require [fabric.js.util :refer [get-and-reset!]]
              cljsjs.fabric))

(enable-console-print!)


(defonce fab    js/fabric)
(defonce canvas (fab.Canvas. "the-canvas"))
(defonce rect   (fab.Rect. (clj->js {:left 100 :top 100 :fill "red" :width 20 :height 20 :angle 45})))
(defonce loop-ref (atom nil))
(defonce last-frame (atom (.now js/performance)))
(def a (atom 1))

(defn render-loop []
  (let [delta (get-and-reset! last-frame (.now js/performance))]
    (.set rect #js {:angle delta})
    (reset! loop-ref (js/requestAnimationFrame render-loop))))

(defn init []
  (.add canvas rect)
  (reset! loop-ref (js/requestAnimationFrame render-loop)))
                     
(defonce initiated (init))
      
(defn on-js-reload []
  (.renderAll canvas)
  (js/cancelAnimationFrame @loop-ref)
  (js/requestAnimationFrame render-loop))


 
()
