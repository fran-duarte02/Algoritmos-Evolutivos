# 🧬 Algoritmos Evolutivos Multiobjetivo — Selección de Partidos del Mundial ⚽

Este proyecto implementa un **algoritmo evolutivo multiobjetivo** para seleccionar los **partidos más convenientes para asistir durante un Mundial de Fútbol**, considerando diferentes grupos de personas con **presupuestos limitados** e **intereses diversos**.

Fue desarrollado como parte de un **proyecto académico en la Facultad de Ingeniería**, en el marco del curso de **Algoritmos Evolutivos**.

---

## Objetivos del algoritmo

El modelo busca **optimizar simultáneamente** dos objetivos principales:

1. **Maximizar el interés del grupo** por los partidos seleccionados  
   - Se consideran variables como la calidad de los equipos, la fase del torneo y las afinidades particulares de los asistentes.

2. **Minimizar el costo total del itinerario**  
   - Incluye precios de entradas, traslados y otros gastos relacionados.

El resultado es un **conjunto de soluciones no dominadas** (frontera de Pareto) que representan distintos compromisos entre **costo** e **interés**, permitiendo explorar diferentes alternativas de planificación.

---

## Metodología

Cada **individuo** de la población representa una **combinación posible de partidos** a los que asistir.  
La evolución del sistema se basa en operadores genéticos clásicos:

- **Selección**
- **Cruce (crossover)**
- **Mutación**

Se utiliza un enfoque de **optimización multiobjetivo basado en dominancia**, similar a **NSGA-II**, para mantener una población de soluciones no dominadas a lo largo de las generaciones.

---

## Características principales

- Enfoque **multiobjetivo real** (balance entre costo e interés).  
- Representación flexible de itinerarios de partidos.  
- Mantenimiento de **diversidad poblacional** durante la evolución.  
- Resultados interpretables mediante la **frontera de Pareto**.  
- Implementación completa en **Java**, sin dependencia de frameworks externos.

---

## 💻 Implementación

El proyecto está desarrollado íntegramente en **Java**, aplicando conceptos de:
- Programación orientada a objetos  
- Diseño modular de clases para operadores genéticos  
- Representación de soluciones y funciones de evaluación  
- Comparación y ordenamiento de individuos mediante dominancia de Pareto  

---

## Contexto académico

Proyecto realizado como parte de la materia **Algoritmos Evolutivos** dentro de la carrera de **Ingeniería en Computación**.  
El objetivo fue aplicar técnicas de **optimización evolutiva multiobjetivo** a un problema real, combinando modelado, programación y análisis de resultados.

---

## Resultados esperados

El algoritmo genera un conjunto de **soluciones óptimas de compromiso**, que permiten al usuario:

- Evaluar distintas combinaciones de partidos.  
- Balancear entre **interés deportivo** y **presupuesto disponible**.  
- Analizar las decisiones mediante la **frontera de Pareto**.
