package kr.co.koboolean.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.koboolean.action.Action;
import kr.co.koboolean.log.action.LoginProcessAction;
import kr.co.koboolean.log.action.LogoutAction;
import kr.co.koboolean.log.action.SigninPageAction;
import kr.co.koboolean.log.action.SigninProcessAction;
import kr.co.koboolean.main.action.NFCCreateProcessAction;
import kr.co.koboolean.main.action.SelectGPSAction;
import kr.co.koboolean.main.action.UpdateFoodAreaAction;
import kr.co.koboolean.main.action.checkGPSAction;
import kr.co.koboolean.main.action.insertFoodAction;
import kr.co.koboolean.main.action.insertFoodAreaAction;
import kr.co.koboolean.main.action.loginFormAction;
import kr.co.koboolean.main.action.nfcStoreAction;
import kr.co.koboolean.main.action.searchAreaAction;
import kr.co.koboolean.main.action.serchNfcTableAction;
import kr.co.koboolean.vo.ActionForward;

/**
 * Servlet implementation class LogFrontController
 */
@WebServlet("*.main")
public class mainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public mainController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());

		ActionForward forward = null;
		Action action = null;
		System.out.println("command = " + command);
		if (command.equals("/signIn.main")) {
			action = new SigninPageAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("/signInProcess.main")) {
			action = new SigninProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("/login.main")) {
			action = new LoginProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("/nfcCreate.main")) {
			action = new NFCCreateProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("/selectgps.main")) {
			action = new SelectGPSAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("/logout.main")) {
			action = new LogoutAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("/insertFoodArea.main")) {
			action = new insertFoodAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("/searchArea.main")) {
			action = new searchAreaAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("/updateFoodArea.main")) {
			action = new UpdateFoodAreaAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("/insertFoodAreaGo.main")) {
			action = new insertFoodAreaAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("/nfcStore.main")) {
			action = new nfcStoreAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("/checkGPS.main")) {
			action = new checkGPSAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("/loginForm.main")) {
			action = new loginFormAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("/serchNfcTable.main")) {
			action = new serchNfcTableAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (forward != null) {
			// �����Ͻ� ������ ���������� ó���Ǿ��� ���
			if (forward.isRedirect()) {
				// redirect �������
				response.sendRedirect(forward.getUrl());
			} else {
				// dispatch �������
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getUrl());
				dispatcher.forward(request, response);
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doProcess(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doProcess(request, response);
	}

}