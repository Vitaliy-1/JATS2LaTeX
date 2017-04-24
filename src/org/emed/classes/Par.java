package org.emed.classes;
/**
 * @file /src/org/emed/classes/Par.java
 *
 * Copyright (c) 2017 Vitaliy Bezsheiko
 * 
 * Distributed under the GNU GPL v3.
 */
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Par extends ParContent {
	private String parContent;

	public String getContent() {	
		return replacement(parContent);
	}

	public void setContent(String parContent) {
		this.parContent = parContent;
		
	}
	
	public static String replacement(String parContent) {
		LinkedHashMap<String, String> forReplacement = new LinkedHashMap<String, String> ();
		forReplacement.put("\\", "\\textbackslash{}");
		forReplacement.put("%", "\\%");
		forReplacement.put("±", "$\\pm$");
		forReplacement.put("∓", "$\\mp$");
		forReplacement.put("<", "\\textless{}");
		forReplacement.put(">", "\\textgreater{}");
		forReplacement.put("≤", "$\\leq$");
		forReplacement.put("≥", "$\\geq$");
		forReplacement.put("μ", "$\\mu$");
		forReplacement.put("α", "$\\alpha$");
		forReplacement.put("β", "$\\beta$");
		forReplacement.put("γ", "$\\gamma$");
		forReplacement.put("Γ", "$\\Gamma$");
		forReplacement.put("δ", "$\\delta$");
		forReplacement.put("Δ", "$\\Delta$");
		forReplacement.put("ϵ", "$\\epsilon$");
		forReplacement.put("ε", "$\\varepsilon$");
		forReplacement.put("ζ", "$\\zeta$");
		forReplacement.put("η", "$\\eta$");
		forReplacement.put("θ", "$\\theta$");
		forReplacement.put("ϑ", "$\\vartheta$");
		forReplacement.put("Θ", "$\\Theta$");
		forReplacement.put("ι", "$\\iota$");
		forReplacement.put("κ", "$\\kappa$");
		forReplacement.put("λ", "$\\lambda$");
		forReplacement.put("Λ", "$\\Lambda$");
		forReplacement.put("μ", "$\\mu$");
		forReplacement.put("ν", "$\\nu$");
		forReplacement.put("ο", "$\\omicron$");
		forReplacement.put("π", "$\\pi$");
		forReplacement.put("Π", "$\\Pi$");
		forReplacement.put("ρ", "$\\rho$");
		forReplacement.put("σ", "$\\sigma$");
		forReplacement.put("Σ", "$\\Sigma$");
		forReplacement.put("τ", "$\\tau$");
		forReplacement.put("υ", "$\\upsilon$");
		forReplacement.put("Υ", "$\\Upsilon$");
		forReplacement.put("ϕ", "$\\varphi$");
		forReplacement.put("Φ", "$\\Phi$");
		forReplacement.put("φ", "$\\phi$");
		forReplacement.put("χ", "$\\chi$");
		forReplacement.put("ψ", "$\\psi$");
		forReplacement.put("Ψ", "$\\Psi$");
		forReplacement.put("ω", "$\\omega$");
		forReplacement.put("Ω", "$\\Omega$");
		forReplacement.put("©", "\\textcopyright{}");
		forReplacement.put("∞", "$\\infty$");
		forReplacement.put("\u2A7D", "$\\leqslant$");
		forReplacement.put("\u2A7E", "$\\geqslant$");
		forReplacement.put("\u039C", "M");
		forReplacement.put("_", "\\textunderscore{}");
		forReplacement.put("#", "\\#");
		for (Entry<String, String> entry : forReplacement.entrySet()) {
			parContent = parContent.replace(entry.getKey(), entry.getValue());			
		}
		parContent = parContent.replaceAll("\\[$|^\\]", "");
		
		return parContent;
	}

}
