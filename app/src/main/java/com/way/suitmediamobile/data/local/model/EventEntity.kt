package com.way.suitmediamobile.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventEntity(
    var id: Int = 0,
    var title: String,
    var subTitle: String,
    var eventDate: String,
    var time: String,
    var image: String,
    var latitude: Double,
    var longitude: Double
) : Parcelable

object Events {
    val listEvent = arrayListOf<EventEntity>(
        EventEntity(
            0,
            "Wonderful Music Event",
            "Kemenparekraf membuat acara bernama Music Matters from Wonderful Indonesia. Konser musik ini menampilkan kolaborasi antar musisi lokal dan internasional di beberapa lokasi Destinasi Super Prioritas yang ada #DiIndonesiaAja, yaitu Danau Toba",
            "4 Des 2020",
            "7:00 PM",
            "https://www.indonesia.travel/content/dam/indtravelrevamp/en/events/microsite-event/lagi-jenuh-tonton-musisi-kece-di-music-matters-from-wonderful-indonesia-ini-aja/thumbnail.jpg",
            2.793159869497649,
            98.61826781962208
        ),
        EventEntity(
            1,
            "Dieng Culture Festival 2022",
            "Acara ini akan dilangsungkan di kompleks Candi Arjuna, Desa Dieng Kulon, Kabupaten Banjarnegara, Jawa Tengah, pada 2 hingga 4 September 2022.",
            "2 Sep 2022",
            "8:00 PM",
            "https://www.indonesia.travel/content/dam/indtravelrevamp/microsite-event/artikel-event/rundown-dan-lineup-dieng-culture-festival-2022/header.jpg",
            -7.2050053517095325, 109.90664990955679
        ),
        EventEntity(
            2,
            "Pesona Jalur Rempah 2022",
            "Coba, deh, datang ke Belitung Timur, Kepulauan Bangka Belitung. Kawasan ini dulu merupakan bagian dari jalur sutra perdagangan rempah dunia, lho. Selain itu, ada banyak hal istimewa yang tersembunyi di sini. Salah satunya adalah event megah bertajuk Jelajah Pesona Jalur Rempah (JPJR).",
            "6 Sep 2022",
            "6:00 PM",
            "https://www.indonesia.travel/content/dam/indtravelrevamp/microsite-event/artikel-event/daya-tarik-jelajah-pesona-jalur-rempah-2022/header.jpg",
            -2.9659538667890066, 108.14919808634572
        ),
    )
}