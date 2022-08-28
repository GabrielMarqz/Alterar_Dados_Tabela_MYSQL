package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AlterarPessoa {

	public static void main(String[] args) throws SQLException {

		Scanner entrada = new Scanner(System.in);

		Connection conexao = FabricaConexao.getConexao();

		System.out.print("Qual o codigo: ");
		int codigo = entrada.nextInt();

		String select = "SELECT codigo, nome FROM pessoas WHERE codigo = ?";
		String update = "UPDATE pessoas SET nome = ? WHERE codigo = ?";
		PreparedStatement stmt = conexao.prepareStatement(select);
		stmt.setInt(1, codigo);

		ResultSet r = stmt.executeQuery();

		if (r.next()) {
			Pessoa p = new Pessoa(r.getInt(1), r.getString(2));

			System.out.println("O nome atual é " + p.getNome());
			entrada.nextLine();

			System.out.print("Qual o novo nome: ");
			String nome = entrada.nextLine();

			stmt.close();
			stmt = conexao.prepareStatement(update);
			stmt.setString(1, nome);
			stmt.setInt(2, codigo);
			stmt.execute();
			System.out.println("Pessoa alterada com sucesso!");
		} else {
			System.out.println("Pessoa não encontrada");
		}

		stmt.close();
		entrada.close();
		conexao.close();
	}

}
