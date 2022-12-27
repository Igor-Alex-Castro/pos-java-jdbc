package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class UserPosDAO {

	private Connection connection;

	public UserPosDAO() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Userposjava userposjava) {
		try {
			String sql = "insert into userposjava ( nome, email) values (?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);

			insert.setString(1, userposjava.getNome());
			insert.setString(2, userposjava.getEmail());
			System.out.println("salvar");
			insert.execute();
			connection.commit(); // salva as informaçoes no banco
		} catch (Exception e) {
			// TODO: handle exception
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	public List<Userposjava> listar() throws SQLException {
		List<Userposjava> list = new ArrayList<Userposjava>();

		String sql = "SELECT * FROM USERPOSJAVA";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Userposjava userposjava = new Userposjava();
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));
			System.out.println(resultado.getLong("id"));
			list.add(userposjava);

		}

		return list;
	}

	public Userposjava buscar(Long id) throws SQLException {
		Userposjava retorno = new Userposjava();

		String sql = "SELECT * FROM USERPOSJAVA WHERE ID = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { // retorna apeans um ou nenhum

			retorno.setId(resultado.getLong("id"));
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));
		}

		return retorno;
	}
	
	public List<BeanUserFone> listaUserFone (Long idUser){
		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();
		
		String  sql = " SELECT NOME, NUMERO, EMAIL FROM TELEFONEUSER AS FONE " ;
		sql += " INNER JOIN USERPOSJAVA AS USERP ";
		sql += " ON FONE.USUARIOPESSOA = USERP.ID";
		sql += " WHERE USERP.ID = " + idUser;
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				BeanUserFone userFone = new BeanUserFone();
				userFone.setEmail(resultSet.getString("email"));
				userFone.setNome(resultSet.getString("nome"));
				userFone.setNome(resultSet.getString("numero"));
				
				beanUserFones.add(userFone);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		return beanUserFones;
	}

	public void atualizar(Userposjava userposjava) {
		try {
			String sql = "UPDATE USERPOSJAVA SET NOME = ?" + "WHERE ID = " + userposjava.getId();

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userposjava.getNome());

			statement.execute();
			connection.commit();

		} catch (Exception e) {
			// TODO: handle exception
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}
	
	public void deletar(Long id) {
		
		try {
			String sql = "DELETE FROM USERPOSJAVA WHERE ID = " + id;
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.execute();
			connection.commit();
			System.out.println("ID deletado " + id);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	public void salvarTelefone(Telefone telefone) {
		try {
			
			String sql = "INSERT INTO public.telefoneuser(numero, tipo, usuariopessoa) VALUES (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());
			
			statement.execute();
			connection.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void deleteFonesPosUser(Long idUser) {
		String sqlFone = "DELETE FROM TELEFONEUSER WHERE USUARIOPESSOA = " + idUser;
		String sqlUser = "DELETE FROM USERPOSJAVA WHERE ID =" + idUser;
		
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(sqlFone);
			preparedStatement.executeUpdate();
			connection.commit();
			
			preparedStatement = connection.prepareStatement(sqlUser);
			preparedStatement.execute();
			connection.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		
	}
	
	
	

}
