package org.ssglobal.training.codes.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailDetails {

	// Class data members
	private String recipient;
	private String msgBody;
	private String subject;
	private String attachment;
}
