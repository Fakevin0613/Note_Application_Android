package com.noteapplication.cs398

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDataAccess {
    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Note::class)
    fun insert(note:Note)

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Folder::class)
    fun insert(folder: Folder)

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Tag::class)
    fun insert(tag: Tag)

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = TagNoteCrossRef::class)
    fun insert(tagNoteCrossRef: TagNoteCrossRef)

    @Update(entity = Note::class)
    fun update(note:Note)

    @Update(entity = Folder::class)
    fun update(folder: Folder)

    @Delete(entity = Note::class)
    fun delete(note: Note)

    @Delete(entity = Folder::class)
    fun delete(folder: Folder)

    @Delete(entity = Tag::class)
    fun delete(tag: Tag)

    @Delete(entity = TagNoteCrossRef::class)
    fun delete(tagNoteCrossRef: TagNoteCrossRef)

    // *** this is the point of modification for filter and ordering feature
    @Query(
        "Select * from `Note` order by id ASC"
    )
    fun getNotes(): LiveData<List<Note>>

    @Query(
        "Select * from `Note` where id = :id"
    )
    fun getNoteById(id:Int): LiveData<List<Note>>

    // *** this is the point of modification for filter and ordering feature
    @Query(
        "Select Tag.* from `Tag`, `TagNoteCrossRef` as `Ref` " +
                "where Ref.noteId = :noteId " +
                "and Tag.id = Ref.tagId " +
                "order by name ASC"
    )
    fun getTags(noteId: Int): LiveData<List<Tag>>

    // *** this is the point of modification for filter and ordering feature
    @Query(
        "Select * from `TagNoteCrossRef` where noteId = :noteId"
    )
    fun getTagRefs(noteId: Int): LiveData<List<TagNoteCrossRef>>

    // *** this is the point of modification for filter and ordering feature
    @Query(
        "Select * from `Folder` order by name ASC"
    )
    fun getFolders(): LiveData<List<Folder>>
}