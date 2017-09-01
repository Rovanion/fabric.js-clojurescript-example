(ns fabric.js.core
  (:require [fabric.js.util :refer [get-and-reset!]]
            cljsjs.fabric))

(enable-console-print!)


(defonce fab    js/fabric)
(defonce canvas (fab.Canvas. "the-canvas"))
(defonce rect   (fab.Rect. #js {:left 100 :top 100 :fill "red" :width 200 :height 200
                                :angle 45 :strokeWidth 5}))
(defonce circle (fab.Circle. #js {:radius 100}))
(defonce loop-ref (atom nil))
(defonce last-frame (atom (.now js/performance)))

(defn refit-canvas []
  (.setHeight canvas (.-innerHeight js/window))
  (.setWidth  canvas (.-innerWidth  js/window)))


(defn render []
  (let [now   (.now js/performance)
        delta (- now (get-and-reset! last-frame now))]
    (.set rect #js {:angle (/ now 100)
                    :fill  (str "rgba("
                                (-> now (/ 1000)       Math/sin (+ 1) (* 128) int) ","
                                (-> now (/ 1100)       Math/cos (+ 1) (* 128) int) ","
                                (-> now (/ 1200) (+ 1) Math/sin (+ 1) (* 128) int) ", 1)")})
    (.renderAll canvas)
    (reset! loop-ref (js/requestAnimationFrame render))))

(defn init []
  (.add canvas rect)
  (.add canvas circle)
  (refit-canvas)
  (.addEventListener js/window "resize" refit-canvas)
  (reset! loop-ref (js/requestAnimationFrame render)))
                     
(defonce initiated (init))
      
(defn on-js-reload []
  (.renderAll canvas)
  (js/cancelAnimationFrame @loop-ref)
  (js/requestAnimationFrame render))


 

