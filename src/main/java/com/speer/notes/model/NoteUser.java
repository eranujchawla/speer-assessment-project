/**
 * 
 */
package com.speer.notes.model;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;

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
public class NoteUser {

	@Id
	private String id;
	private String name;
	@UniqueElements
	private String email;
	private Integer age;
	private String department;
}
