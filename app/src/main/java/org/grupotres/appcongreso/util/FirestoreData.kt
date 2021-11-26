package org.grupotres.appcongreso.util

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.core.Speaker
import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.util.*

object FirestoreData {

	fun loadData() {
		val dbRef = Firebase.firestore
		val lecture1 = dbRef.collection("lectures").document("lecture1")
		lecture1.set(
			Lecture(
				id = "1",
				title = "Miércoles 1",
				startTime = Date.from(Instant.from(LocalDateTime.of(2021, Month.DECEMBER, 1, 0, 0))),
				endTime = 	Date.from(Instant.from(LocalDateTime.of(2021, Month.DECEMBER, 1, 0, 0))),
				capacity = 0,
				description = "",
				speaker = null,
				topic = "",
				url = "",
				room = "Sala1|Sala2",
				day = "Miércoles",
				isHeader = true,
			)
		)
		val lecture2 = dbRef.collection("lectures").document("lecture2")
		lecture2.set(
			Lecture(
				id = "2",
				title = "Investigación e innovación para posicionar a la madera como un material del futuro sostenible en la industria de la Construcción",
				startTime = Date.from(Instant.from(LocalDateTime.of(2021, Month.DECEMBER, 1, 8, 30))),
				endTime = 	Date.from(Instant.from(LocalDateTime.of(2021, Month.DECEMBER, 1, 9, 30))),
				capacity = 100,
				description = "La madera es uno de los materiales que más crecimiento ha experimentado en los últimos años. Desplazado por el hormigón en el boom del desarrollo inmobiliario, ahora se presenta como un material alternativo sostenible en el tiempo, con huella de carbono negativa y alineado con los ODS.",
				speaker = Speaker(
					id = "2",
					surname = "Guindos",
					maternalSurname = "Bretones",
					name = "Pablo",
					country = "Chile",
					company = "Centro UC de Innovación en Madera",
					academicInfo = "Especialista en el Diseño y Construcción de Estructuras de Madera. Pertenece al Departamento de Ingeniería Estructural y Geotécnica, Departamento de Ingeniería y Gestión de la Construcción. Además es Director Académico del Centro UC de Innovación en Madera.",
					uriPhoto = "https://eventos.ucontinental.edu.pe/wp-content/uploads/2020/11/Pablo-Guindos-Bretones-uc-300x300.jpg"
				),
				topic = "Construcción",
				url = "",
				room = "Sala1",
				day = "Miércoles",
				isHeader = false,
			)
		)
		val lecture3 = dbRef.collection("lectures").document("lecture3")
		lecture3.set(
			Lecture(
				id = "3",
				title = "Principales avances, indicadores y proyectos que posicionan a la madera en la construcción",
				startTime = Date.from(Instant.from(LocalDateTime.of(2021, Month.DECEMBER, 1, 9, 30))),
				endTime = 	Date.from(Instant.from(LocalDateTime.of(2021, Month.DECEMBER, 1, 10, 30))),
				capacity = 100,
				description = "La construcción con madera se remonta al período del Neolítico, o incluso antes, momento en que el ser humano comenzó a utilizar troncos para construir refugios y pequeñas chozas.",
				speaker = Speaker(
					id = "1",
					surname = "Castaño",
					maternalSurname = "Victorero",
					name = "Felipe",
					country = "Chile",
					company = "Pontificia Universidad Católica de Chile",
					academicInfo = "Arquitecto, MSc MSc Sustainable Building Technology, Enviromental Design and Energy efficiency. University of Nottingham. Actualmente es Subdirector de Transferencia del Centro UC de Innovación en Madera e investigador de la Escuela de Arquitectura de la Pontificia Universidad Católica de Chile.",
					uriPhoto = "https://eventos.ucontinental.edu.pe/wp-content/uploads/2020/11/falipe-victorero-uc-300x300.jpg"
				),
				topic = "Construcción",
				url = "",
				room = "Sala1",
				day = "Miércoles",
				isHeader = false,
			)
		)
		val lecture4 = dbRef.collection("lectures").document("lecture4")
		lecture4.set(
			Lecture(
				id = "4",
				title = "Industrialización 4.0 y el enfoque en la educación en ingeniería",
				startTime = Date.from(Instant.from(LocalDateTime.of(2021, Month.DECEMBER, 1, 10, 30))),
				endTime = 	Date.from(Instant.from(LocalDateTime.of(2021, Month.DECEMBER, 1, 11, 30))),
				capacity = 100,
				description = "Actualmente la industria está experimentando una transformación hacia los procesos de fabricación inteligente y digitalización completa, surgiendo nuevas tecnologías de información y comunicación como los sistemas cibernéticos, ciberseguridad, internet de las cosas, Big Data, sistema de integración, computación en la nube, fabricación digital e inteligente, entre otros.",
				speaker = Speaker(
					id = "4",
					surname = "Guarnizo",
					maternalSurname = "Gomez",
					name = "Rodrigo",
					country = "Colombia",
					company = "Festo Didáctica CESAM",
					academicInfo = "Ingeniero de Alimentos con especialización en Mecatrónica. MBA en Administración de Empresas y Liderazgo. Actualmente labora como gerente de Festo Didáctica CESAM.",
					uriPhoto = "https://eventos.ucontinental.edu.pe/wp-content/uploads/2020/11/Rodrigo-Guarnizo-Gomez-uc-300x300.jpg"
				),
				topic = "Ingeniería",
				url = "",
				room = "Sala1",
				day = "Miércoles",
				isHeader = false,
			)
		)
		val lecture5 = dbRef.collection("lectures").document("lecture5")
		lecture5.set(
			Lecture(
				id = "5",
				title = "Diseño paisajístico de cierre de minas en la región Andina",
				startTime = Date.from(Instant.from(LocalDateTime.of(2021, Month.DECEMBER, 1, 11, 30))),
				endTime = 	Date.from(Instant.from(LocalDateTime.of(2021, Month.DECEMBER, 1, 12, 30))),
				capacity = 100,
				description = "Desde tiempos remotos, el Perú ha sido un país minero y, en la coyuntura actual, ese sector representa una fuente significativa de ingresos debido a la explotación y los beneficios extraídos de los yacimientos mineralizados.",
				speaker = Speaker(
					id = "3",
					surname = String.Empty,
					maternalSurname = "Macera",
					name = "Margarita",
					country = "Bélgica",
					company = "Centro de Competencias del Agua",
					academicInfo = "Investigadora de Doctorado en Ciencias y Magíster de Ciencias en Urbanismo y Planeamiento Estratégico (Ku Leuven, Bélgica). Investigadora Asociada del Centro de Competencias del Agua (Ayacucho, Perú)",
					uriPhoto = "https://eventos.ucontinental.edu.pe/wp-content/uploads/2020/11/Margarita-Macera-belgica-uc-300x300.jpg"
				),
				topic = "Minería",
				url = "",
				room = "Sala1",
				day = "Miércoles",
				isHeader = false,
			)
		)
	}
}