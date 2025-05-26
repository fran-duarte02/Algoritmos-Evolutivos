# Algoritmos-Evolutivos
Algoritmo evolutivo multiobjetivo con el propósito de poder elegir los partidos más convenientes para asistir durante un Mundial de Fútbol, considerando múltiples grupos de personas con presupuestos limitados e intereses diversos.
El algoritmo optimiza simultáneamente dos objetivos principales:

Maximizar el interés del grupo por los partidos seleccionados, en función de variables como la calidad de los equipos, la fase del torneo, y afinidades particulares.

Minimizar el costo total del itinerario, teniendo en cuenta precios de entradas, traslados y otros gastos asociados.

Cada individuo de la población representa una posible combinación de partidos a los que asistir. La evolución se basa en operadores genéticos clásicos (selección, cruce y mutación), y se aplica un enfoque de optimización multiobjetivo basado en dominancia (como NSGA-II o similar) para mantener un conjunto de soluciones no dominadas que representan distintos compromisos entre costo e interés.

Este enfoque permite a los usuarios explorar múltiples opciones de planificación, equilibrando preferencias subjetivas y restricciones presupuestarias de forma eficiente.
