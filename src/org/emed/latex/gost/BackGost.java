package org.emed.latex.gost;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import org.emed.classes.BibName;
import org.emed.classes.Bibitem;
import org.emed.classes.BibitemBook;
import org.emed.classes.BibitemChapter;
import org.emed.classes.BibitemConf;
import org.emed.classes.BibitemJournal;

public class BackGost {
	
	public static void back(BufferedWriter wrobj, List<Bibitem> bibitems) throws IOException {
		wrobj.newLine();
		wrobj.write("\\begin{thebibliography}" + "{" + bibitems.size() + "}");
		wrobj.newLine();
		for (Bibitem bibitem : bibitems) {
			wrobj.write("\\bibitem{" + bibitem.getId() + "}");
			List<Bibitem> bibcontents = bibitem.getBibitem();
			for (Bibitem bibcontent : bibcontents) {
				if (bibcontent.getClass().getName() == "org.emed.classes.BibitemJournal") {
					BibitemJournal bibJournal = (BibitemJournal) bibcontent;
					
					if (bibJournal.getName() != null) {
						List<BibName> bibNames = bibJournal.getName();
						for (int i = 0; i < bibNames.size(); i++) {
							
							 if (bibNames.size() < 4) {
								wrobj.write(bibNames.get(0).getSurname() + " ");
								char[] initials = bibNames.get(0).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
								}
								break;
							}
						}
					} 
					//set article title
					if (bibJournal.getTitle() != null) {	
						wrobj.write(bibJournal.getTitle().trim());
					}
					//set article collab
					if (bibJournal.getCollab() != null) {
						wrobj.write(" / " + bibJournal.getCollab().trim());
					}
					//set authors second time
					if (bibJournal.getName() != null && bibJournal.getCollab() == null) {
						wrobj.write(" / ");
						List<BibName> bibNames = bibJournal.getName();
						for (int i = 0; i < bibNames.size(); i++) {
							if (i == 0 && bibNames.size() > 1) {
								char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + ", ");	
						    } else if (i == 0 && bibNames.size() == 1) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname());	
						    } else if (i == 1 && bibNames.size() > 2) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + ", ");
						    } else if (i == 1 && bibNames.size() == 2) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + " ");
						    } else if (i == 2 && bibNames.size() == 3) {
						    	char[] initials = bibNames.get(i).getInitials();
						    	for (int y = 0; y < initials.length; y++) {
						    		wrobj.write(initials[y] + ". ");
						    	}
						        wrobj.write(bibNames.get(i).getSurname());
						    } else if (i == 2 && bibNames.size() > 3) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname());
						    } else if (i > 2) {
						    	wrobj.write(" [et al.]");
						    	break;
						    }
						}
					}
					
					// set journal name
					if (bibJournal.getSource() != null) {
						wrobj.write(" // " + bibJournal.getSource().trim());
					}
					// set year
					if (bibJournal.getYear() != 0) {
						wrobj.write(". - " + bibJournal.getYear());
					}
					// set issue and volume
					if (bibJournal.getVolume() != 0) {
						wrobj.write(". - V. " + bibJournal.getVolume());
					} 
					if ((bibJournal.getIssue() != 0) && (bibJournal.getVolume() != 0)) {
						wrobj.write(", I. " + bibJournal.getIssue());
					} else if ((bibJournal.getIssue() != 0) && (bibJournal.getVolume() == 0)) {
						wrobj.write(". - I. " + bibJournal.getIssue());
					}
					// set fpage
					if (bibJournal.getFpage() != null && bibJournal.getLpage() != 0) {
						wrobj.write(". - P. " + bibJournal.getFpage().trim());
					} else if (bibJournal.getFpage() != null && bibJournal.getLpage() == 0) {
						wrobj.write(". - " + bibJournal.getFpage().trim() + ".");
					}
					if (bibJournal.getLpage() != 0) {
						wrobj.write("-" + bibJournal.getLpage() + ".");
					}
					// set url
					/*
					if (bibJournal.getDoi() != null) {
						wrobj.write(", retrived from: https://doi.org/" + bibJournal.getDoi());
					} else if (bibJournal.getPmid() != null) {
						wrobj.write(", retrived from: " + bibJournal.getPmid());
					} else if (bibJournal.getUrl() != null) {
						wrobj.write(", retrived from: " + bibJournal.getUrl());
					}
					*/
				} else if (bibcontent.getClass().getName() == "org.emed.classes.BibitemBook") {
					BibitemBook bibitemBook = (BibitemBook) bibcontent;
					if (bibitemBook.getName() != null) {
						List<BibName> bibNames = bibitemBook.getName();
						for (int i = 0; i < bibNames.size(); i++) {
							
							 if (bibNames.size() < 4) {
								wrobj.write(bibNames.get(0).getSurname() + " ");
								char[] initials = bibNames.get(0).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
								}
								break;
							}
						}
					} 
					if (bibitemBook.getSource() != null) {	
						wrobj.write(bibitemBook.getSource().trim());
					}
					
					// set authors second time
					
					if (bibitemBook.getName() != null && bibitemBook.getCollab() == null) {
						wrobj.write(" / ");
						List<BibName> bibNames = bibitemBook.getName();
						for (int i = 0; i < bibNames.size(); i++) {
							if (i == 0 && bibNames.size() > 1) {
								char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + ", ");	
						    } else if (i == 0 && bibNames.size() == 1) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname());	
						    } else if (i == 1 && bibNames.size() > 2) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + ", ");
						    } else if (i == 1 && bibNames.size() == 2) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname());
						    } else if (i == 2 && bibNames.size() == 3) {
						    	char[] initials = bibNames.get(i).getInitials();
						    	for (int y = 0; y < initials.length; y++) {
						    		wrobj.write(initials[y] + ". ");
						    	}
						        wrobj.write(bibNames.get(i).getSurname());
						    } else if (i == 2 && bibNames.size() > 3) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname());
						    } else if (i > 2) {
						    	wrobj.write(" [et al.]");
						    	break;
						    }
						}
					}
					
					if (bibitemBook.getCollab() != null) {
						wrobj.write(" / " + bibitemBook.getCollab().trim());
					}
					// set publisher location
					if (bibitemBook.getPublisherLoc() != null) {
						wrobj.write(". - " + bibitemBook.getPublisherLoc().trim());
					}
					// set publisher name
					if (bibitemBook.getPublisherName() != null && bibitemBook.getPublisherLoc() != null) {
						wrobj.write(": " + bibitemBook.getPublisherName());
					} else if (bibitemBook.getPublisherName() != null && bibitemBook.getPublisherLoc() == null) {
						wrobj.write(". - " + bibitemBook.getPublisherName());
					}
					// set year
					if (bibitemBook.getYear() != 0) {
						wrobj.write(", " + bibitemBook.getYear() + ".");
					}
					/* Chapter 
					 * 
					 * */
				} else if (bibcontent.getClass().getName() == "org.emed.classes.BibitemChapter") {
					BibitemChapter bibitemChapter = (BibitemChapter) bibcontent;
					if (bibitemChapter.getName() != null) {
						List<BibName> bibNames = bibitemChapter.getName();
						for (int i = 0; i < bibNames.size(); i++) {
							
							 if (bibNames.size() < 4) {
								wrobj.write(bibNames.get(0).getSurname() + " ");
								char[] initials = bibNames.get(0).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
								}
								break;
							}
						}
					} 
					if (bibitemChapter.getChapterTitle() != null) {	
						wrobj.write(bibitemChapter.getChapterTitle().trim());
					}
					
					// set authors second time
					
					if (bibitemChapter.getName() != null && bibitemChapter.getCollabAuthor() == null) {
						wrobj.write(" / ");
						List<BibName> bibNames = bibitemChapter.getName();
						for (int i = 0; i < bibNames.size(); i++) {
							if (i == 0 && bibNames.size() > 1) {
								char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + ", ");	
						    } else if (i == 0 && bibNames.size() == 1) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname());	
						    } else if (i == 1 && bibNames.size() > 2) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + ", ");
						    } else if (i == 1 && bibNames.size() == 2) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + " ");
						    } else if (i == 2 && bibNames.size() == 3) {
						    	char[] initials = bibNames.get(i).getInitials();
						    	for (int y = 0; y < initials.length; y++) {
						    		wrobj.write(initials[y] + ". ");
						    	}
						        wrobj.write(bibNames.get(i).getSurname());
						    } else if (i == 2 && bibNames.size() > 3) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname());
						    } else if (i > 2) {
						    	wrobj.write(" [et al.]");
						    	break;
						    }
						}
					}
					if (bibitemChapter.getCollabAuthor() != null) {
						wrobj.write(" / " + bibitemChapter.getCollabAuthor().trim());
					}
					if (bibitemChapter.getSource() != null) {
						wrobj.write(" // " + bibitemChapter.getSource());
					}
					if (bibitemChapter.getEditor() != null && bibitemChapter.getCollabEditor() == null) {
						wrobj.write(" / ed.: ");
						List<BibName> bibNames = bibitemChapter.getEditor();
						for (int i = 0; i < bibNames.size(); i++) {
							if (i == 0 && bibNames.size() > 1) {
								char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + ", ");	
						    } else if (i == 0 && bibNames.size() == 1) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname());	
						    } else if (i == 1 && bibNames.size() > 2) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + ", ");
						    } else if (i == 1 && bibNames.size() == 2) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + " ");
						    } else if (i == 2 && bibNames.size() == 3) {
						    	char[] initials = bibNames.get(i).getInitials();
						    	for (int y = 0; y < initials.length; y++) {
						    		wrobj.write(initials[y] + ". ");
						    	}
						        wrobj.write(bibNames.get(i).getSurname());
						    } else if (i == 2 && bibNames.size() > 3) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname());
						    } else if (i > 2) {
						    	wrobj.write(" [et al.]");
						    	break;
						    }
						}
					}
					// set publisher location
					if (bibitemChapter.getPublisherLoc() != null) {
						wrobj.write(". - " + bibitemChapter.getPublisherLoc().trim());
					}
					// set publisher name
					if (bibitemChapter.getPublisherName() != null && bibitemChapter.getPublisherLoc() != null) {
						wrobj.write(": " + bibitemChapter.getPublisherName());
					} else if (bibitemChapter.getPublisherName() != null && bibitemChapter.getPublisherLoc() == null) {
						wrobj.write(". - " + bibitemChapter.getPublisherName());
					}
					// set year
					if (bibitemChapter.getYear() != 0) {
						wrobj.write(", " + bibitemChapter.getYear() + ".");
					}
					// set fpage
					if (bibitemChapter.getFpage() != null && bibitemChapter.getLpage() != 0) {
						wrobj.write(". - P. " + bibitemChapter.getFpage().trim());
					} else if (bibitemChapter.getFpage() != null && bibitemChapter.getLpage() == 0) {
						wrobj.write(". - " + bibitemChapter.getFpage().trim() + ".");
					}
					if (bibitemChapter.getLpage() != 0) {
						wrobj.write("-" + bibitemChapter.getLpage() + ".");
					}
				} else if (bibcontent.getClass().getName() == "org.emed.classes.BibitemConf") {
					BibitemConf bibitemConf = (BibitemConf) bibcontent;
					if (bibitemConf.getName() != null) {
						List<BibName> bibNames = bibitemConf.getName();
						for (int i = 0; i < bibNames.size(); i++) {
							
							 if (bibNames.size() < 4) {
								wrobj.write(bibNames.get(0).getSurname() + " ");
								char[] initials = bibNames.get(0).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
								}
								break;
							}
						}
					} 
					if (bibitemConf.getSource() != null) {	
						wrobj.write(bibitemConf.getSource().trim());
					}
					// authors second time
					if (bibitemConf.getName() != null && bibitemConf.getName() == null) {
						wrobj.write(" / ed.: ");
						List<BibName> bibNames = bibitemConf.getName();
						for (int i = 0; i < bibNames.size(); i++) {
							if (i == 0 && bibNames.size() > 1) {
								char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + ", ");	
						    } else if (i == 0 && bibNames.size() == 1) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname());	
						    } else if (i == 1 && bibNames.size() > 2) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + ", ");
						    } else if (i == 1 && bibNames.size() == 2) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname() + " ");
						    } else if (i == 2 && bibNames.size() == 3) {
						    	char[] initials = bibNames.get(i).getInitials();
						    	for (int y = 0; y < initials.length; y++) {
						    		wrobj.write(initials[y] + ". ");
						    	}
						        wrobj.write(bibNames.get(i).getSurname());
						    } else if (i == 2 && bibNames.size() > 3) {
						    	char[] initials = bibNames.get(i).getInitials();
								for (int y = 0; y < initials.length; y++) {
									wrobj.write(initials[y] + ". ");
							    }
								wrobj.write(bibNames.get(i).getSurname());
						    } else if (i > 2) {
						    	wrobj.write(" [et al.]");
						    	break;
						    }
						}
					}
					if (bibitemConf.getConfname() != null) {
						wrobj.write(" // " + bibitemConf.getConfname());
					}
					if (bibitemConf.getConfdate() != null) {
						wrobj.write(", " + bibitemConf.getConfdate());
					}
					if (bibitemConf.getConfloc() != null) {
						wrobj.write(". - " + bibitemConf.getConfloc());
					}
					if (bibitemConf.getYear() != 0) {
						wrobj.write(", " + bibitemConf.getYear() + ".");
					}
					
				}
			} //end of bibitem
			
			wrobj.newLine();
			
		}
		wrobj.write("\\end{thebibliography}");
	    wrobj.newLine();
	    wrobj.write("\\end{document}");
	    wrobj.close();
		
	}
	
}
