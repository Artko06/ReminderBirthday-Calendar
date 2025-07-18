# ==============================================
# Правила из missing_rules.txt
# ==============================================

-keep class com.artkotlindev.data.local.repository.ContactAppRepositoryImpl { *; }
-keep class com.artkotlindev.data.local.repository.EventRepositoryImpl { *; }
-keep class com.artkotlindev.data.local.repository.ExportFileRepositoryImpl { *; }
-keep class com.artkotlindev.data.local.repository.ImportFileRepositoryImpl { *; }
-keep class com.artkotlindev.data.local.repository.SettingsRepositoryImpl { *; }
-keep class com.artkotlindev.data.local.roomDb.dao.EventDao { *; }
-keep class com.artkotlindev.data.local.roomDb.dao.SettingsDao { *; }
-keep class com.artkotlindev.data.local.util.image.ImageUtilsKt { *; }
-keep class com.artkotlindev.data.local.util.init.InitDataStore { *; }
-keep class com.artkotlindev.data.local.util.init.InitRoomDb { *; }
-keep class com.artkotlindev.data.local.util.serialization.Deserialization { *; }
-keep class com.artkotlindev.data.remote.repository.GoogleClientRepositoryImpl { *; }
-keep class com.artkotlindev.data.remote.util.google.GoogleAuthClient { *; }
-keep class com.artkotlindev.data.remote.util.google.GoogleDriveClient { *; }

# ==============================================
# Сохраняем методы доступа к DAO в Database классе
# ==============================================

-keepclassmembers class com.artkotlindev.data.local.roomDb.database.CalendarInfoDB {
    public *;
}