package com.example.appassignment
import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.Scanner

object FileUtility {

    private const val DIARY_FILE_NAME = "diary_entries.txt"

    fun saveToFile(context: Context, data: String) {
        try {
            val file = File(context.filesDir, DIARY_FILE_NAME)
            val writer = FileWriter(file, true)
            writer.append(data)
            writer.flush()
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun readFromFile(context: Context): List<String> {
        val entries = mutableListOf<String>()
        try {
            val file = File(context.filesDir, DIARY_FILE_NAME)
            Scanner(file).use { scanner ->
                while (scanner.hasNextLine()) {
                    entries.add(scanner.nextLine())
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return entries
    }
    fun searchByDate(context: Context, targetDate: String): List<String> {
        val entries = mutableListOf<String>()
        try {
            val file = File(context.filesDir, DIARY_FILE_NAME)
            Scanner(file).use { scanner ->
                while (scanner.hasNextLine()) {
                    val line = scanner.nextLine()
                    if (line.startsWith(targetDate)) {
                        entries.add(line)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return entries
    }
    fun deleteNotesForDate(context: Context, date: String) {
        try {
            val file = File(context.filesDir, DIARY_FILE_NAME)
            val updatedEntries = mutableListOf<String>()

            Scanner(file).use { scanner ->
                while (scanner.hasNextLine()) {
                    val line = scanner.nextLine()
                    if (!line.startsWith("$date ")) {
                        updatedEntries.add(line)
                    }
                }
            }

            // Rewrite the file without notes for the specified date
            file.writeText(updatedEntries.joinToString("\n"))

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun clearFile(context: Context) {
        try {
            val file = File(context.filesDir, DIARY_FILE_NAME)
            file.writeText("") // Clear the content of the file
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
