/**
 * 
 */
package com.speer.notes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chawl
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notes")
public class Note {

	@Id
	private String id;
	private String user;
	private String[] keywords;
	
}
