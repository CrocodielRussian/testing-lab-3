#include "title.typ"

#set page(
  paper: "a4",
  margin: (top: 2cm, bottom: 2cm, left: 2.5cm, right: 1.5cm),
  numbering: "1",
)
#set text(font: "Times New Roman", size: 12pt, lang: "ru")
#set par(justify: true)
#set heading(numbering: "1.1.")
#show link: underline

#show heading: it => [
  #v(0.5em)
  #it
  #v(0.5em)
]

#pagebreak()

= Описание задания

Сформировать варианты использования, разработать на их основе тестовое покрытие покрытие и провести функциональное тестирование интерфейса сайта (в соответствии с вариантом).

*Вариант:* 131415: Google Analytics - http://analytics.google.com/


= Выполнение
== Код
https://github.com/CrocodielRussian/testing-lab-3


