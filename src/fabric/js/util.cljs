(ns fabric.js.util)

(defn get-and-reset!
  "Resets an atom and returns the last value it held."
  [ref new-value]
  (let [old-value @ref]
    (reset! ref new-value)
    old-value))
                     
