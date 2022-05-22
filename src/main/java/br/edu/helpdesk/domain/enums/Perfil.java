package br.edu.helpdesk.domain.enums;

public enum Perfil {
	BAIXA(0, "BAIXA"), 
	MEDIA(1, "MEDIA"), 
	ALTA(2, "ALTA");
	
	private Integer codigo;
	private String descricao;
	
	
	private Perfil(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum (Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(Perfil x : Perfil.values()) {
			if (cod.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Prioridade inválida!");
	}
	
}
