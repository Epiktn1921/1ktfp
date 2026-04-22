(defn start
  "Инициализирует новую игру с диапазоном от low до high.
   Возвращает строку 'Я готов...'"
  [low high]
  (reset! current-range [low high])
  (reset! last-guess nil)
  "Я готов...")

(defn- make-guess
  "Вычисляет среднее значение диапазона [low high] для следующего предположения"
  [low high]
  (quot (+ low high) 2))


(defn guess-my-number
  "Возвращает текущее предположение программы.
   Использует бинарный поиск: выбирает середину текущего диапазона."
  []
  (if-let [[low high] @current-range]
    (let [guess (make-guess low high)]
      (reset! last-guess guess)
      guess)
    "Сначала вызовите (start low high)"))


(defn smaller
  "Сообщает программе, что загаданное число меньше текущего предположения.
   Сужает диапазон поиска: [low, guess-1]"
  []
  (if-let [[low high] @current-range]
    (if-let [guess @last-guess]
      (let [new-high (dec guess)
            new-range [low (max low new-high)]  ;; защита от некорректного диапазона
            new-guess (make-guess (first new-range) (second new-range))]
        (reset! current-range new-range)
        (reset! last-guess new-guess)
        new-guess)
      "Сначала вызовите (guess-my-number)")
    "Сначала вызовите (start low high)"))


(defn bigger
  "Сообщает программе, что загаданное число больше текущего предположения.
   Сужает диапазон поиска: [guess+1, high]"
  []
  (if-let [[low high] @current-range]
    (if-let [guess @last-guess]
      (let [new-low (inc guess)
            new-range [(min high new-low) high]  ;; защита от некорректного диапазона
            new-guess (make-guess (first new-range) (second new-range))]
        (reset! current-range new-range)
        (reset! last-guess new-guess)
        new-guess)
      "Сначала вызовите (guess-my-number)")
    "Сначала вызовите (start low high)"))
